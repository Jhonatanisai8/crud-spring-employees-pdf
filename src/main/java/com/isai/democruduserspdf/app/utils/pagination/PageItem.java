package com.isai.democruduserspdf.app.utils.pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PageItem {
    private Integer number;
    private Boolean current;

    public PageItem(Integer number, Boolean current) {
        this.number = number;
        this.current = current;
    }
}
