package com.isai.democruduserspdf.app.utils.pagination;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PageRender<T> {
    private String url;
    private Page<T> page;
    private Integer totalPages;
    private Integer currentPage;
    private Integer numberElementsPage;
    private List<PageItem> pageItems;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pageItems = new ArrayList<PageItem>();
        this.numberElementsPage = 5;
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        int start, end;
        if (totalPages <= numberElementsPage) {
            start = 1;
            end = totalPages;
        } else {
            if (currentPage <= numberElementsPage / 2) {
                start = 1;
                end = numberElementsPage;
            } else if (currentPage >= totalPages - numberElementsPage / 2) {
                start = totalPages - numberElementsPage + 1;
                end = numberElementsPage;
            } else {
                start = currentPage - numberElementsPage / 2;
                end = numberElementsPage;
            }
        }

        for (int i = 0; i < end; i++) {
            pageItems.add(new PageItem(start + i, currentPage == start + i));
        }
    }

    public Boolean isLast() {
        return page.isLast();
    }

    public Boolean isHasNext() {
        return page.hasNext();
    }

    public Boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
