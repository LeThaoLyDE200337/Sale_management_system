package com.lylt.SalesManagement.core;

import com.lylt.sales_management_system.Product;
import com.lylt.sales_management_system.SalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

public class SalesServiceTest {
    private SalesService service;
    
    @BeforeEach 
    void setUp() { 
        service = new SalesService(); 
    }

    // YÊU CẦU: calculateSubtotal() Ít nhất 3 test ------------------------
    @Test 
    void testCalculateSubtotal_Normal() {
        assertEquals(1500, service.calculateSubtotal(new Product("P1", "Laptop", 500, 3)));
    }
    
    @Test 
    void testCalculateSubtotal_QuantityOne() {
        assertEquals(150, service.calculateSubtotal(new Product("P2", "Mouse", 150, 1)));
    }

    @Test 
    void testCalculateSubtotal_NullProduct() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateSubtotal(null));
    }

    // YÊU CẦU: calculateDiscount() Ít nhất 6 test (Đã bao gồm đúng 6 mốc Boundary Value trong đề)
    @ParameterizedTest
    @CsvSource({
        "999.99, 0", 
        "1000, 50", 
        "4999.99, 249.9995", 
        "5000, 60", 
        "9999.99, 999.999", 
        "10000, 1500"
    })
    void testCalculateDiscount_Boundary(double subtotal, double expectedDiscount) {
        assertEquals(expectedDiscount, service.calculateDiscount(subtotal), 0.001);
    }
    
    @Test 
    void testCalculateDiscount_Negative() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateDiscount(-10));
    }

    // YÊU CẦU: calculateShippingFee() Ít nhất 3 test ---------------------
    @ParameterizedTest
    @CsvSource({
        "1999.99, 50", 
        "2000, 0", 
        "2500, 0"
    })
    void testCalculateShippingFee(double subtotal, double expectedFee) {
        assertEquals(expectedFee, service.calculateShippingFee(subtotal));
    }
    
    @Test 
    void testCalculateShippingFee_Negative() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateShippingFee(-10));
    }

    // YÊU CẦU: calculateTotal() Ít nhất 2 test ---------------------------
    @Test 
    void testCalculateTotal_WithShippingNoDiscount() {
        // Price 400 * 2 = 800 (Subtotal). Discount = 0. Shipping = 50. Total = 850.
        assertEquals(850, service.calculateTotal(new Product("P3", "Keyboard", 400, 2)));
    }

    @Test 
    void testCalculateTotal_WithDiscountNoShipping() {
        // Price 1000 * 3 = 3000 (Subtotal). Discount = 3000*0.05 = 150. Shipping = 0. Total = 2850.
        assertEquals(2850, service.calculateTotal(new Product("P4", "Monitor", 1000, 3)));
    }

    // YÊU CẦU: classifyCustomer() Ít nhất 4 test -------------------------
    @ParameterizedTest
    @CsvSource({
        "999.99, REGULAR", 
        "1000, SILVER", 
        "4999.99, SILVER", 
        "5000, GOLD", 
        "9999.99, GOLD", 
        "10000, VIP"
    })
    void testClassifyCustomer_Boundary(double total, String expectedType) {
        assertEquals(expectedType, service.classifyCustomer(total));
    }
    
    @Test 
    void testClassifyCustomer_Negative() {
        assertThrows(IllegalArgumentException.class, () -> service.classifyCustomer(-100));
    }
}