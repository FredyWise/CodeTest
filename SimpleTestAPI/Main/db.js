// db.js
const mysql = require('mysql2/promise');

const pool = mysql.createPool({
    host: 'localhost',
    user: 'testing',
    password: 'testing',
    database: 'testapi'
});


async function getAllProducts() {
    const connection = await pool.getConnection();
    try {
        const [results] = await connection.query('SELECT * FROM car_brand');
        return results;
    } catch (error) {
        console.error('Error executing query:', error);
        throw error;
    } finally {
        connection.release();
    }
}

async function getProductById(productId) {
    const connection = await pool.getConnection();
    try {
        const [result] = await connection.query('SELECT * FROM car_brand WHERE cd_brand = ?', [productId]);
        return result;
    } catch (error) {
        console.error('Error executing query:', error);
        throw error;
    } finally {
        connection.release();
    }
}

async function addProduct(product) {
    const connection = await pool.getConnection();
    try {
        const [result] = await connection.query('INSERT INTO car_brand SET ?', product);
        return result.insertId;
    } catch (error) {
        console.error('Error executing query:', error);
        throw error;
    } finally {
        connection.release();
    }
}

async function updateProduct(productId, updatedProduct) {
    const connection = await pool.getConnection();
    try {
        const [result] = await connection.query('UPDATE car_brand SET ? WHERE cd_brand = ?', [updatedProduct, productId]);
        return result.affectedRows;
    } catch (error) {
        console.error('Error executing query:', error);
        throw error;
    } finally {
        connection.release();
    }
}

async function deleteProduct(productId) {
    const connection = await pool.getConnection();
    try {
        const [result] = await connection.query('DELETE FROM car_brand WHERE cd_brand = ?', [productId]);
        return result.affectedRows;
    } catch (error) {
        console.error('Error executing query:', error);
        throw error;
    } finally {
        connection.release();
    }
}

async function searchProduct(query) {
    const connection = await pool.getConnection();
    try {
        const [result] = await connection.query('SELECT * FROM car_brand WHERE desc_brand LIKE ?', ['%' + query + '%']);
        return result;
    } catch (error) {
        console.error('Error executing query:', error);
        throw error;
    } finally {
        connection.release();
    }
}

module.exports = {
    getAllProducts,
    getProductById,
    addProduct,
    updateProduct,
    deleteProduct,
    searchProduct
};
