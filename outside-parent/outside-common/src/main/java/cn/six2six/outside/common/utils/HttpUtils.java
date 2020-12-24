/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.utils;

import cn.six2six.outside.common.exception.OutsideException;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 基于<a href="https://square.github.io/okhttp">OKHttp3</a>的HTTP请求封装.
 */
public final class HttpUtils {

    /**
     * 最大空闲连接数.
     */
    private static final Integer MAX_IDLE_CONNECTIONS = 10;
    /**
     * 连接保持时长, 分钟.
     */
    private static final Integer KEEP_ALIVE_DURATION = 5;
    /**
     * 连接超时时间, 秒.
     */
    private static final Integer TIMEOUT_CONNECT = 30;
    /**
     * 读超时时间, 秒.
     */
    private static final Integer TIMEOUT_READ = 30;
    /**
     * 写超时时间, 秒.
     */
    private static final Integer TIMEOUT_WRITE = 10;

    /**
     * 设置最大连接数和每主机最大连接数.
     */
    private static final Dispatcher DISPATCHER;
    /**
     * {@link OkHttpClient}.
     */
    private static final OkHttpClient HTTP;
    /**
     * 忽略证书的HTTP客户端.
     */
    private static final OkHttpClient HTTP_SSL;

    static {
        /**
         * 初始化{@link OkHttpClient}.
         */
        DISPATCHER = new Dispatcher();
        DISPATCHER.setMaxRequests(64);
        DISPATCHER.setMaxRequestsPerHost(5);

        HTTP =
                new OkHttpClient.Builder().
                        dispatcher(DISPATCHER).
                        connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES)).
                        connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS).
                        writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS).
                        readTimeout(TIMEOUT_READ, TimeUnit.SECONDS).
                        build();

        HTTP_SSL =
                new OkHttpClient.Builder().
                        dispatcher(DISPATCHER).
                        connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES)).
                        connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS).
                        writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS).
                        readTimeout(TIMEOUT_READ, TimeUnit.SECONDS).
                        sslSocketFactory(_getSSLSocketFactory(), _x509TrustManager()).
                        hostnameVerifier(_getHostnameVerifier()).
                        build();
    }

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_XML = MediaType.parse("application/xml");
    private static final MediaType MEDIA_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private HttpUtils() {
        super();
    }

    /**
     * GET请求, 返回值通过{@link Function}转换成对象.
     *
     * @param url               请求地址.
     * @param headers           请求头.
     * @param responseConverter {@link Function}, 响应值转换接口.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T get(String url, Map<String, String> headers, Function<String, T> responseConverter) {
        Request.Builder requestBuilder = _build(url, headers);
        return _request(requestBuilder, responseConverter, HTTP);
    }

    /**
     * GET请求, 返回值通过{@link Function}转换成对象.
     *
     * @param url     请求地址.
     * @param headers 请求头.
     * @return 返回.
     */
    public static Response get(String url, Map<String, String> headers) {
        Request.Builder requestBuilder = _build(url, headers);

        return _request(requestBuilder, HTTP);
    }

    /**
     * get请求
     *
     * @param url               请求地址.
     * @param headers           请求头.
     * @param responseConverter {@link Function}, 响应值转换接口.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T getSSL(String url, Map<String, String> headers, Function<String, T> responseConverter) {
        Request.Builder requestBuilder = _build(url, headers);
        return _request(requestBuilder, responseConverter, HTTP_SSL);
    }


    /**
     * POST请求JSON格式消息体, 返回值通过{@link Function}转换成对象.
     *
     * @param url               请求地址.
     * @param headers           请求头.
     * @param valueAsJSON       请求JSON消息体.
     * @param responseConverter {@link Function}, 响应值转换接口.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T postJSON(String url, Map<String, String> headers, String valueAsJSON, Function<String, T> responseConverter) {
        return post(MEDIA_TYPE_JSON, url, headers, valueAsJSON, responseConverter);
    }

    /**
     * POST请求JSON格式消息体, 返回值通过{@link Function}转换成对象.
     *
     * @param url               请求地址.
     * @param headers           请求头.
     * @param value             请求消息体.
     * @param responseConverter {@link Function}, 响应值转换接口.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T post(MediaType mediaType, String url, Map<String, String> headers, String value, Function<String, T> responseConverter) {
        Request.Builder requestBuilder = _build(url, headers);
        /**
         * 设置JSON请求消息体.
         */
        requestBuilder.post(RequestBody.create(mediaType, value));
        return _request(requestBuilder, responseConverter, HTTP);
    }

    /**
     * POST请求JSON格式消息体(SSL), 返回值通过{@link Function}转换成对象.
     *
     * @param url               请求地址.
     * @param headers           请求头.
     * @param valueAsJSON       请求JSON消息体.
     * @param responseConverter {@link Function}, 响应值转换接口.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T postJSONSSL(String url, Map<String, String> headers, String valueAsJSON, Function<String, T> responseConverter) {
        return postSSL(MEDIA_TYPE_JSON, url, headers, valueAsJSON, responseConverter);
    }

    /**
     * POST请求JSON格式消息体(SSL), 返回值通过{@link Function}转换成对象.
     *
     * @param url               请求地址.
     * @param headers           请求头.
     * @param value             请求消息体.
     * @param responseConverter {@link Function}, 响应值转换接口.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T postSSL(MediaType mediaType, String url, Map<String, String> headers, String value, Function<String, T> responseConverter) {
        Request.Builder requestBuilder = _build(url, headers);
        /**
         * 设置JSON请求消息体.
         */
        requestBuilder.post(RequestBody.create(mediaType, value));
        return _request(requestBuilder, responseConverter, HTTP_SSL);
    }

    /**
     * POST请求JSON格式消息体, 返回{@link Class&lt;T&gt;}类型的值对象.
     *
     * @param url         请求地址.
     * @param headers     请求头.
     * @param valueAsJSON 请求JSON消息体.
     * @param clazz       值对象的{@link Class}.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T postJSON(String url, Map<String, String> headers, String valueAsJSON, Class<T> clazz) {
        return postJSON(url, headers, valueAsJSON, value -> JSON.parseObject(value, clazz));
    }

    /**
     * POST请求JSON格式消息体, 返回{@link Class&lt;T&gt;}类型的值对象.
     *
     * @param url         请求地址.
     * @param headers     请求头.
     * @param paramMap    请求body.
     * @param clazz       值对象的{@link Class}.
     * @param <T>
     * @return 值对象.
     */
    public static <T> T postForm(String url, Map<String, String> headers, Map<String, String> paramMap, Class<T> clazz) {
        Request.Builder requestBuilder = _build(url, headers);
        /**
         * 设置JSON请求消息体.
         */
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> param : paramMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(param.getKey()).append("=").append(param.getValue());
        }
        requestBuilder.post(RequestBody.create(MEDIA_TYPE_FORM, sb.toString()));
        return _request(requestBuilder, value -> JSON.parseObject(value, clazz), HTTP);
    }

    /**
     * 携带证书的HTTP
     *
     * @param certPath 证书地址
     * @param certPass 证书密码
     * @return
     */
    private static OkHttpClient _getHTTPSSLCERTClient(String certPath, String certPass) {
        OkHttpClient HTTP_SSL_CERT = new OkHttpClient.Builder().
                dispatcher(DISPATCHER).
                connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES)).
                connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS).
                writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS).
                readTimeout(TIMEOUT_READ, TimeUnit.SECONDS).
                sslSocketFactory(_getSSLContext(certPath, certPass).getSocketFactory(), _x509TrustManager()).
                hostnameVerifier(_getHostnameVerifier()).
                build();
        return HTTP_SSL_CERT;
    }

    /**
     * 内部构建{@link Request.Builder}.
     *
     * @param url     请求地址.
     * @param headers 请求头.
     * @return {@link Request.Builder}.
     */
    private static Request.Builder _build(String url, Map<String, String> headers) {
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        /**
         * 设置头.
         */
        Optional.
                ofNullable(headers).
                ifPresent(
                        headersAsNotNull ->
                                headersAsNotNull.entrySet().stream().forEach(entry -> requestBuilder.addHeader(entry.getKey(), entry.getValue()))
                );
        return requestBuilder;
    }

    /**
     * 发起请求, 并获取返回值通过{@link Function}转换成值对象.
     *
     * @param requestBuilder    {@link Request.Builder}.
     * @param responseConverter {@link Function}, 值对象转换接口.
     * @param <T>
     * @return 值对象.
     */
    private static <T> T _request(Request.Builder requestBuilder, Function<String, T> responseConverter, OkHttpClient httpClient) {
        try (Response response = httpClient.newCall(requestBuilder.build()).execute()) {
            /*if (!response.isSuccessful()) {
                throw new OutsideException(new IOException("Unexpected code " + response));
            }*/
            return responseConverter.apply(response.body().string());
        } catch (IOException ex) {
            throw new OutsideException(ex);
        }
    }

    /**
     * 发起请求, 并获取返回值通过{@link Function}转换成值对象.
     *
     * @param requestBuilder {@link Request.Builder}.
     * @return 原始的请求信息.
     */
    private static Response _request(Request.Builder requestBuilder, OkHttpClient httpClient) {
        try (Response response = httpClient.newCall(requestBuilder.build()).execute()) {
            /*if (!response.isSuccessful()) {
                throw new OutsideException(new IOException("Unexpected code " + response));
            }*/
            return response;
        } catch (IOException ex) {
            throw new OutsideException(ex);
        }
    }

    /**
     * 文件下载时段大小设置.
     */
    private static final int DOWNLOAD_CHUNK_SIZE = 2048;

    /**
     * 下载文件, 并保存到指定的路径.
     *
     * @param url        文件请求地址.
     * @param path       保存的目标路径, {@link Path}.
     * @param progressor 文件下载进度通知接口.
     */
    public static void download(String url, Path path, Consumer<Integer> progressor) {
        _createFileIfNotExisting(path);

        Request request = _build(url, null).build();

        BufferedSource source = null;
        BufferedSink sink = null;
        try (Response response = HTTP.newCall(request).execute()) {
            ResponseBody body = response.body();

            long contentLength = body.contentLength();
            source = body.source();

            sink = Okio.buffer(Okio.sink(path));
            Buffer sinkBuffer = sink.buffer();

            long totalBytesRead = 0;
            for (long bytesRead; (bytesRead = source.read(sinkBuffer, DOWNLOAD_CHUNK_SIZE)) != -1; ) {
                /**
                 * {@link BufferedSink#emit()}必须被调用, 否则如果文件足够大会耗尽内存.
                 */
                sink.emit();
                totalBytesRead += bytesRead;
                progressor.accept((int) ((totalBytesRead * 100) / contentLength));
            }

        } catch (IOException ex) {
            throw new OutsideException(ex);
        } finally {
            try {
                if (sink != null) {
                    sink.flush();
                    sink.close();
                }

                if (source != null) {
                    source.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 下载文件, 并保存到指定的路径.
     *
     * @param url        文件请求地址.
     * @param path       保存的目标路径.
     * @param progressor 文件下载进度通知接口.
     */
    public static void download(String url, String path, Consumer<Integer> progressor) {
        download(url, Paths.get(path), progressor);
    }

    /**
     * 如果文件不存在, 创建.
     *
     * @param path 文件路径, {@link Path}.
     * @throws OutsideException 尝试创建一次, 如果仍然创建不成功, 扔出{@link OutsideException}.
     */
    private static void _createFileIfNotExisting(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                throw new OutsideException(ex);
            }

            /**
             * 不存在创建一次, 如果仍然不存在报错.
             */
            if (!Files.exists(path)) {
                throw new OutsideException(String.format("Create %s failed", path));
            }
        }
    }

    /**
     * SSL通道工程构造器.
     *
     * @return {@link SSLSocketFactory}.
     */
    private static SSLSocketFactory _getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, _getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 信任管理器队列.
     * <p>
     * 忽略所有证书，不抛出异常.
     * </p>
     *
     * @return {@link TrustManager[]}.
     */
    private static TrustManager[] _getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }

    /**
     * Hostname校验器.
     * <p>
     * 直接返回TRUE.
     * </p>
     *
     * @return {@link HostnameVerifier}.
     */
    private static HostnameVerifier _getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    /**
     * 获取X509TrustManager管理器.
     *
     * @return {@link X509TrustManager}.
     */
    private static X509TrustManager _x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private static SSLContext _getSSLContext(String certPath, String certPass) {
        try {
            // 证书
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            Resource resource = new FileSystemResource(certPath);
            InputStream inputStream = resource.getInputStream();
            char[] passArray = certPass.toCharArray();
            clientStore.load(inputStream, passArray);
            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, passArray);
            // 创建 SSLContext
            KeyManager[] kms = kmf.getKeyManagers();
            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(kms, null, new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            throw new OutsideException("设置证书出错, path=" + certPath);
        }
    }

    /**
     * 通过URL地址获取图片流
     * @param url 请求地址.
     * @return
     */
    public static byte[] getImag(String url){
        Request request = new Request.Builder().url(url).build();
        try (Response response = HTTP.newCall(request).execute()) {
            if(response.code() == 200){
                InputStream is  = response.body().byteStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while( (len=is.read(buffer)) != -1 ){
                    outStream.write(buffer, 0, len);
                }
                is.close();
                return outStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * okhttp上传二进制文件
     * @param filename 文件名称
     * @param url 请求地址.
     * @param content 文件字节内容
     * @return
     */
    public static String sendFile(String filename,String url,byte[] content){
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),content);
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM).addFormDataPart("filename",filename,fileBody)
                .build();
        Request request = new Request.Builder()
                .addHeader("Connection","close")
                .post(body)
                .url(url)
                .build();
        try (Response response = HTTP.newCall(request).execute()) {
            if(response.code() == 200){
                String result = response.body().string();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
