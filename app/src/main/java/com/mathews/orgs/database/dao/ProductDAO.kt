package com.mathews.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mathews.orgs.model.Product

@Dao
interface ProductDAO {
    @Query("SELECT * from product")
    fun indexAll(): List<Product>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg product: Product);

    @Update
    fun update(product: Product);

    @Delete
    fun delete(product: Product);

    @Query("SELECT * FROM product WHERE product.id = :id")
    fun findById(id: Long): Product?;
}