package com.twilio

import com.twilio.dao.ProductDAO
import java.awt.image.MultiPixelPackedSampleModel

class ProductController {
    private val dao = ProductDAO()

    fun getProductById(idProduct: Int): ProductDAO.Product {
        return dao.getProductById(idProduct)
    }

    fun getCategories(): MutableList<String> {
        return dao.getCategories()
    }

    fun getProducts(): MutableList<ProductDAO.Product> {
        return dao.getProducts()
    }

    fun getProductsByCategory(id_category: Int): MutableList<ProductDAO.Product> {
        return dao.getProductsByCategory(id_category)
    }
}