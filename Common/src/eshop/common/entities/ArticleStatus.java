package eshop.common.entities;

public enum ArticleStatus {

    INACTIVE("Inactive"),
    ACTIVE("Active");

    private String displayValue;

    ArticleStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public static ArticleStatus fromString(String text) {
        for (ArticleStatus status : ArticleStatus.values()) {
            if (status.displayValue.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
