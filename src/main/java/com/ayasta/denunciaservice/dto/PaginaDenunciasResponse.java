package com.ayasta.denunciaservice.dto;

import java.util.List;

public class PaginaDenunciasResponse {

    private List<DenunciaResponse> items;
    private int offset;
    private int limit;
    private long total;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginaDenunciasResponse(List<DenunciaResponse> items, int offset, int limit, long total) {
        this.items = items;
        this.offset = offset;
        this.limit = limit;
        this.total = total;
        this.hasPrevious = offset > 0;
        this.hasNext = (long) offset + items.size() < total;
    }

    public List<DenunciaResponse> getItems() {
        return items;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public long getTotal() {
        return total;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }
}
