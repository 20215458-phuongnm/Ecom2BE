package com.mygame.repository.product;

import com.mygame.entity.inventory.DocketVariant;
import com.mygame.entity.product.Brand;
import com.mygame.entity.product.Category;
import com.mygame.entity.product.Product;
import com.mygame.entity.product.Variant;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.Join;
import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    //Laays brand có sp
    default List<Brand> findByCategorySlug(String slug) {
        Specification<Brand> spec = (root, query, cb) -> {
            Join<Brand, Product> product = root.join("products");
            Join<Product, Category> category = product.join("category");

            query.distinct(true);
            query.where(cb.equal(category.get("slug"), slug));
            query.orderBy(cb.asc(root.get("name")));

            return query.getRestriction();
        };

        return findAll(spec);
    }

    default List<Brand> findBySearchQuery(String searchQuery) {
        Specification<Brand> spec = (root, query, cb) -> {
            Join<Brand, Product> product = root.join("products");
            Join<Product, Category> category = product.join("category");
            Join<Product, Variant> variant = product.join("variants");
            Join<Variant, DocketVariant> docketVariant = variant.join("docketVariants");

            query.distinct(true);
            query.where(cb.or(
                    cb.like(product.get("name"), "%" + searchQuery + "%"),
                    cb.like(product.get("slug"), "%" + searchQuery + "%"),
                    cb.like(category.get("name"), "%" + searchQuery + "%"),
                    cb.like(root.get("name"), "%" + searchQuery + "%")
            ));
            query.orderBy(cb.asc(root.get("name")));

            return query.getRestriction();
        };

        return findAll(spec);
    }

    @Query("SELECT COUNT(b.id) FROM Brand b")
    int countByBrandId();

}
