package cn.six2six.outside.dal.export.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : ZengRong
 * @date : 2021-01-29 17:26
 * @description : 图片基本信息
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PicturesInfo {

    private int minRow;
    private int maxRow;
    private int minCol;
    private int maxCol;
    private String ext;
    private byte[] pictureData;
}
