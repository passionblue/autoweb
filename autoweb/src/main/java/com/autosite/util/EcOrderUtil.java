package com.autosite.util;

import static com.autosite.OrderConstants.*;

import java.util.concurrent.atomic.AtomicInteger;

import com.autosite.db.EcOrder;
import com.autosite.db.EcOrderItem;
import com.autosite.ec.EcCart;
import com.autosite.ec.EcCartItem;
import com.jtrend.util.TimeNow;

public class EcOrderUtil {
    
    public static int getStatusValue(String statusStr){
        
        if (statusStr == null) return OrderStatusInvalid;
        if (statusStr.equals(OrderStatusStrReceived)) return OrderStatusReceived;
        else if (statusStr.equals(OrderStatusStrApproved)) return OrderStatusApproved;
        else if (statusStr.equals(OrderStatusStrInProcess)) return OrderStatusInProcess;
        else if (statusStr.equals(OrderStatusStrFulfilled)) return OrderStatusFulfilled;
        else if (statusStr.equals(OrderStatusStrShipped)) return OrderStatusShipped;
        else if (statusStr.equals(OrderStatusStrHalt)) return OrderStatusHalt;
        else if (statusStr.equals(OrderStatusStrCancelled)) return OrderStatusCancelled;
        else if (statusStr.equals(OrderStatusStrReturned)) return OrderStatusReturned;
        else return OrderStatusInvalid;
    }
    
    public static String getStatusStr(int status){
        
        switch(status){
        
        case OrderStatusReceived: return OrderStatusStrReceived;
        case OrderStatusApproved: return OrderStatusStrApproved;
        case OrderStatusInProcess: return OrderStatusStrInProcess;
        case OrderStatusFulfilled: return OrderStatusStrFulfilled;
        case OrderStatusShipped: return OrderStatusStrShipped;
        case OrderStatusHalt: return OrderStatusStrHalt;
        case OrderStatusCancelled: return OrderStatusStrCancelled;
        case OrderStatusReturned: return OrderStatusStrReturned;
        default: return "N/A";
        }
    }
    
    private static AtomicInteger m_counter = new  AtomicInteger(0);
    
    public static String getOrderNumber(boolean anonymous){

        if (anonymous) 
            return "A" + System.nanoTime() + ":" + m_counter.incrementAndGet();
        else 
            return "U" + System.nanoTime() + ":" + m_counter.incrementAndGet();
        
    }

    public static EcOrder convertToAnonymousOrder(long siteId, EcCart cart){

        EcOrder order = new EcOrder();
        
        order.setSiteId(siteId);
        order.setUserId(cart.getUserId());

        order.setUserId(cart.getUserId());
        order.setTimeReceived(new TimeNow());

        order.setOrderStatus(OrderStatusReceived);
        order.setOrderNum(getOrderNumber(true));
        return order;
    }
    
    
    public static EcOrderItem convertToOrderItem(EcOrder order, EcCartItem cartItem){
        EcOrderItem orderItem = new EcOrderItem(); 

        orderItem.setOrderId(order.getId());
        
        orderItem.setProductId(cartItem.getProduct().getId());
        orderItem.setSiteSku(cartItem.getProduct().getSiteSku());

        orderItem.setQty(cartItem.getCount());
        orderItem.setUnitPrice(cartItem.getOrderPirce());
        orderItem.setOrderPrice(cartItem.getCount()*cartItem.getOrderPirce());
        
        orderItem.setColorVariation(cartItem.getColor());
        orderItem.setSizeVariation(cartItem.getSize());
        
        orderItem.setTimeCreated(new TimeNow());
        
        return orderItem;
    }
    
    public static int getPaymentType(String str){
        return PaymentTypeCreditCard; 
    }
    
    public static String formatPhone(String val){
        if (val == null) return null;
        
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);
            
            if (Character.isDigit(c))
                buf.append(val.charAt(i));
        }
        
        return buf.toString();
    }
    
}
