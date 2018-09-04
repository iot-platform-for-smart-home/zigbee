package com.bupt.ZigbeeResolution.data;

import lombok.Data;

@Data
public class Task {
    byte taskType;
    String taskId;
    String taskName;
    int taskNumber;

    public Task(){}

    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"taskType\":")
                .append("\"" + taskType + "\"").append(",");
        sb.append("\"taskId\":")
                .append("\"" + taskId + "\"").append(",");
        sb.append("\"taskName\":")
                .append("\"" + taskName + "\"").append(",");
        sb.append("\"taskNumber\":")
                .append("\"" + taskNumber + "\"");
        sb.append('}');
        return sb.toString();
    }
}
