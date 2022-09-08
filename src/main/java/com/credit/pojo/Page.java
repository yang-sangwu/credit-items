package com.credit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private Integer code;

    private String msg;

    private Integer pageNo;

    private Integer pageSize;

    private Integer pageTotal;

    private Integer count;

    private List<T> date;

    public static <T> Page<T> error(Integer code, String msg) {
        return new Page<T>(code, msg, null, null, null, null, null);
    }
}
