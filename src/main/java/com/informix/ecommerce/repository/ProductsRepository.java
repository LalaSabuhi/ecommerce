package com.informix.ecommerce.repository;

import com.informix.ecommerce.entity.ISellerProducts;
import com.informix.ecommerce.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Integer> {
    @Query(value = "SELECT COUNT(s.user_id) as totalCustomer, " +
            "j.product_id as productPostId, " +
            "j.product_title as productTitle, " +
            "j.price as productPrice, " +
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


    @Query(value = "SELECT * FROM products p INNER JOIN product_location l ON p.product_location_id = l.id " +
            "WHERE (:computer IS NULL OR p.product_category LIKE 'Computer' AND p.product_title LIKE %:computer%) " +
            "AND (:phone IS NULL OR p.product_category LIKE 'Phone' AND p.product_title LIKE %:phone%) " +
            "AND (:laptop IS NULL OR p.product_category LIKE 'Laptop' AND p.product_title LIKE %:laptop%) " +
            "AND (:camera IS NULL OR p.product_category LIKE 'Camera' AND p.product_title LIKE %:camera%) " +
            "AND (:location IS NULL OR l.city LIKE %:location% OR l.country LIKE %:location% OR l.state LIKE %:location%)", nativeQuery = true)
    List<Products> searchWithoutDate(@Param("computer") String computer,
                                     @Param("phone") String phone,
                                     @Param("location") String location,
                                     @Param("laptop") String laptop,
                                     @Param("camera") String camera);

    @Query(value = "SELECT * FROM products p INNER JOIN product_location l ON p.product_location_id = l.id " +
            "WHERE (:computer IS NULL OR p.product_category LIKE 'Computer' AND p.product_title LIKE %:computer%) " +
            "AND (:phone IS NULL OR p.product_category LIKE 'Phone' AND p.product_title LIKE %:phone%) " +
            "AND (:laptop IS NULL OR p.product_category LIKE 'Laptop' AND p.product_title LIKE %:laptop%) " +
            "AND (:camera IS NULL OR p.product_category LIKE 'Camera' AND p.product_title LIKE %:camera%) " +
            "AND (:location IS NULL OR l.city LIKE %:location% OR l.country LIKE %:location% OR l.state LIKE %:location%) " +
            "AND (p.posted_date >= :searchDate)", nativeQuery = true)
    List<Products> search(@Param("computer") String computer,
                          @Param("phone") String phone,
                          @Param("location") String location,
                          @Param("laptop") String laptop,
                          @Param("camera") String camera,
                          @Param("searchDate") LocalDate searchDate);


}
