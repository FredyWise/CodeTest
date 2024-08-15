// db.js
const mysql = require('mysql2/promise');

const pool = mysql.createPool({
    host: 'localhost',
    user: 'testing',
    password: 'testing',
    database: 'BootcampBeriJalan'
});


async function getAllProducts() {
    const connection = await pool.getConnection();
    try {
        const [results] = await connection.query('SELECT * FROM buah');
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
        const [result] = await connection.query('SELECT * FROM buah WHERE id = ?', [productId]);
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
        const [result] = await connection.query('INSERT INTO buah SET ?', product);
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
        const [result] = await connection.query('UPDATE buah SET ? WHERE id = ?', [updatedProduct, productId]);
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
        const [result] = await connection.query('DELETE FROM buah WHERE id = ?', [productId]);
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
        const [result] = await connection.query('SELECT * FROM buah WHERE name LIKE ?', ['%' + query + '%']);
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
