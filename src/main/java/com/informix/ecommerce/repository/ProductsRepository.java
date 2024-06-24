package com.informix.ecommerce.repository;

import com.informix.ecommerce.entity.ISellerProducts;
import com.informix.ecommerce.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Integer> {
    @Query(value = "SELECT COUNT(s.user_id) as totalCustomer, " +
            "j.product_id as productPostId, " +
            "j.product_title as productTitle, " +
            " j.product_image as productImage," +
            "l.id as locationId, " +
            "l.city, " +
            "l.state, " +
            "l.country, " +
            "c.id as companyId, " +
            "c.name " +
            "FROM products j " +
            "INNER JOIN product_location l ON j.product_location_id = l.id " +
            "INNER JOIN product_company c ON j.product_company_id = c.id " +
            "LEFT JOIN product_apply s ON s.product = j.product_id " +
            "WHERE j.posted_by_id = :seller " +
            "GROUP BY j.product_id,j.product_title,j.product_image, l.id, l.city, l.state, l.country, c.id, c.name",
            nativeQuery = true)
    List<ISellerProducts> getSellerProducts(@Param("seller") int seller);




}
