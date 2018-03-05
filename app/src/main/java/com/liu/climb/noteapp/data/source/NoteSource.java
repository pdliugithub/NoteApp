package com.liu.climb.noteapp.data.source;

import java.io.Serializable;
import java.util.List;

/**
 * @author pd_liu on 2018/2/27.
 *         <p>
 *         Note笔记数据
 *         </p>
 */

public class NoteSource implements Serializable {

    private static final String TAG_LOG = "NoteSource";

    private static final long serialVersionUID = -3583946526554669478L;

    /**
     * 创建日期
     */
    private String createDate;

    /**
     * 更新的记录
     */
    private List<String> updateDates;

    /**
     * 笔记的文本
     */
    private String text;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<String> getUpdateDates() {
        return updateDates;
    }

    public void setUpdateDates(List<String> updateDates) {
        this.updateDates = updateDates;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteSource{" +
                "createDate='" + createDate + '\'' +
                ", updateDates=" + updateDates +
                ", text='" + text + '\'' +
                '}';
    }
}
