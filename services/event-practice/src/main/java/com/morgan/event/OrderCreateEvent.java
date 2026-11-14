package com.morgan.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

/**
 * ClassName: OrderCreateEvent
 * Package: com.morgan.event
 *
 * @author Morgan Wei
 * @create 2026-11-13-17:53
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateEvent {
    String orderName;
    BigDecimal orderAmount;
}
