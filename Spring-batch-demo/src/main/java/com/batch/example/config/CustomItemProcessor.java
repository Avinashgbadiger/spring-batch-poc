package com.batch.example.config;

import com.batch.example.model.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessor implements ItemProcessor<Product, Product> {

  @Override
  public @Nullable Product process(Product item) throws Exception {
    //Transformation logic will be handled here
    //Calculate Discounted price = Original price and discount
/*    int discountedPrice = Integer.parseInt(item.getDiscountedPrice());
    double originalPrice = Double.parseDouble(item.getPrice());
    double discount = (discountedPrice/100)*originalPrice;
    double finalPrice = originalPrice - discount;
    item.setDiscountedPrice(String.valueOf(finalPrice));*/

    return item;
  }
}
