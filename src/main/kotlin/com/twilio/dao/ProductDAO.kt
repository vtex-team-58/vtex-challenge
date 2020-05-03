package com.twilio.dao

import com.twilio.utils.DataBaseConnection
import java.math.BigDecimal
import java.sql.PreparedStatement

class ProductDAO {
    private val conn = DataBaseConnection.getConnection()

    data class Product (
        var id_product: Int = 0,
        var nm_product: String = "",
        var ds_product: String = "",
        var vl_product: BigDecimal = BigDecimal.ZERO,
        var nm_category: String = ""
    )

    data class Category (
        var id_category: Int = 0,
        var nm_category: String = "",
        var ds_category: String = ""
    )

    fun getCategories(): MutableList<String> {
        val sql = "SELECT nm_category " +
                "FROM tb_category"
        val ps: PreparedStatement = conn.prepareStatement(sql)
        val rs = ps.executeQuery()
        var categories = mutableListOf<String>()
        while (rs.next()) {
            categories.add(rs.getString("nm_category"))
        }
        rs.close()
        ps.close()
        conn.close()
        return categories
    }

    fun getProductById(idProduct: Int): Product {
        val sql = "SELECT id_product, nm_product, ds_product, vl_price, nm_category " +
            "FROM tb_products " +
            "INNER JOIN tb_category USING(id_category) " +
            "WHERE id_product = ? "

        val ps: PreparedStatement = conn.prepareStatement(sql)
        var i = 0
        ps.setInt(++i, idProduct)
        val rs = ps.executeQuery()
        var product = Product()
        while (rs.next()) {
            product.id_product = rs.getInt("id_product")
            product.nm_product = rs.getString("nm_product")
            product.ds_product = rs.getString("ds_product")
            product.vl_product = rs.getBigDecimal("vl_price")
            product.nm_category = rs.getString("nm_category")
        }
        rs.close()
        ps.close()
        conn.close()
        return product
    }

    fun getProducts(): MutableList<Product> {
        val sql = "SELECT nm_category " +
                "FROM tb_category " +
                "LIMIT 20"
        val ps: PreparedStatement = conn.prepareStatement(sql)
        val rs = ps.executeQuery()
        var products = mutableListOf<Product>()
        while (rs.next()) {
            var product = Product()
            product.id_product = rs.getInt("id_product")
            product.nm_product = rs.getString("nm_product")
            product.ds_product = rs.getString("ds_product")
            product.vl_product = rs.getBigDecimal("vl_price")
            product.nm_category = rs.getString("nm_category")
            products.add(product)
        }
        rs.close()
        ps.close()
        conn.close()
        return products
    }

    fun getProductsByCategory(id_category: Int): MutableList<Product> {
        val sql = "SELECT id_product, nm_product, ds_product, vl_price, nm_category " +
                "FROM tb_products " +
                "INNER JOIN tb_category USING(id_category) " +
                "WHERE tb_products.id_category = ? "
        val ps: PreparedStatement = conn.prepareStatement(sql)
        var i = 0
        ps.setInt(++i, id_category)
        val rs = ps.executeQuery()
        var products = mutableListOf<Product>()
        while (rs.next()) {
            var product = Product()
            product.id_product = rs.getInt("id_product")
            product.nm_product = rs.getString("nm_product")
            product.ds_product = rs.getString("ds_product")
            product.vl_product = rs.getBigDecimal("vl_price")
            product.nm_category = rs.getString("nm_category")
            products.add(product)
        }
        rs.close()
        ps.close()
        conn.close()
        return products
    }
}