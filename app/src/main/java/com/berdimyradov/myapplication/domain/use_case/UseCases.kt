package com.berdimyradov.myapplication.domain.use_case

data class UseCases(
    val deleteProduct: DeleteProduct,
    val getProducts: GetProducts,
    val insertProduct: InsertProduct,
    val searchProduct: SearchProduct,
    val deleteAllItem: DeleteAllItem
)
