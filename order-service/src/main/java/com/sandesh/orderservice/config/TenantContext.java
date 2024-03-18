package com.sandesh.orderservice.config;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = ThreadLocal.withInitial(() -> "order_asia");

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }
}
