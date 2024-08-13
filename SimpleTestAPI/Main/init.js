const express = require('express');
const {
    getAllProducts,
    getProductById,
    addProduct,
    updateProduct,
    deleteProduct,
    searchProduct
} = require('../Main/db');
const {
    toResponse,
    toErrorResponse,
    toSuccessResponse
} = require('../Main/Util/mappers');

const apiKey = "7def4ec4deab71e2c5911ee718db181c8bf077582e9cc397af95c76fb0d459f0"
const app = express();
app.use(express.json());

const PORT = 8081;

function checkHeaders(req, res, next) {
    console.log('Headers:', req.headers);

    if (!req.headers['apikey'] || 
        !req.headers['x-content-type-options'] || 
        !req.headers['x-xss-protection'] || 
        !req.headers['strict-transport-security'] || 
        !req.headers['x-frame-options']) {

        const response = toErrorResponse("Missing required headers")
        return res.status(400).json(response);
    } else if (req.headers['apikey'] != apiKey) {

        const response = toErrorResponse("You do not have permission to access the API!")
        return res.status(400).json(response);
    }

    next();
}

app.use(checkHeaders);

app.listen(PORT, () => console.log(`Server is running on http://localhost:${PORT}`));

app.get('/', (req, res) => {
    const response = toResponse('Welcome to our e-commerce website!');
    res.status(200).json(response);
    console.log('GET request to /');
});

app.get('/api/products', async (req, res) => {
    try {
        const products = await getAllProducts();
        const response = toResponse(products);
        res.status(200).json(response);
        console.log('GET request to /api/products');
    } catch (error) {
        console.error('Error fetching products:', error);
        const errorResponse = toErrorResponse('Internal Server Error');
        res.status(500).json(errorResponse);
    }
});

app.get('/api/products/:id', async (req, res) => {
    const productId = req.params.id;
    try {
        const product = await getProductById(productId);
        if (!product.length) {
            const errorResponse = toErrorResponse('Product not found');
            return res.status(404).json(errorResponse);
        }
        const response = toResponse(product);
        res.status(200).json(response);
        console.log(`GET request to /api/products/${productId}`);
        console.log(`Product with ID ${productId} found.`);
        console.log('Product details:', product);
    } catch (error) {
        console.error(`Error fetching product with ID ${productId}:`, error);
        const errorResponse = toErrorResponse('Internal Server Error');
        res.status(500).json(errorResponse);
    }
});

app.post('/api/products', async (req, res) => {
    if (!req.body) {
        const errorResponse = toErrorResponse('Request body is missing');
        return res.status(400).json(errorResponse);
    }
    console.log(req.body);
    const newProduct = req.body;
    try {
        const productId = await addProduct(newProduct);
        const response = toSuccessResponse(`Product with ID ${productId} added.`);
        res.status(200).json(response);
        console.log('POST request to /api/products');
        console.log('New product created:', newProduct);
    } catch (error) {
        console.error('Error adding new product:', error);
        const errorResponse = toErrorResponse('Internal Server Error');
        res.status(500).json(errorResponse);
    }
});

app.put('/api/products/:id', async (req, res) => {
    const productId = parseInt(req.params.id);
    const updatedProduct = req.body;
    try {
        const affectedRows = await updateProduct(productId, updatedProduct);
        if (affectedRows === 0) {
            const errorResponse = toErrorResponse('Product not found');
            return res.status(404).json(errorResponse);
        }
        const response = toSuccessResponse(`Product with ID ${productId} updated.`);
        res.status(200).json(response);
        console.log(`PUT request to /api/products/${productId}`);
        console.log(`Product with ID ${productId} updated.`);
        console.log('Updated product details:', updatedProduct);
    } catch (error) {
        console.error(`Error updating product with ID ${productId}:`, error);
        const errorResponse = toErrorResponse('Internal Server Error');
        res.status(500).json(errorResponse);
    }
});

app.delete('/api/products/:id', async (req, res) => {
    const productId = parseInt(req.params.id);
    try {
        const affectedRows = await deleteProduct(productId);
        if (affectedRows === 0) {
            const errorResponse = toErrorResponse('Product not found');
            return res.status(404).json(errorResponse);
        }
        const response = toSuccessResponse(`Product with ID ${productId} deleted`);
        res.status(200).json(response);
        console.log(`DELETE request to /api/products/${productId}`);
        console.log(`Product with ID ${productId} deleted.`);
    } catch (error) {
        console.error(`Error deleting product with ID ${productId}:`, error);
        const errorResponse = toErrorResponse('Internal Server Error');
        res.status(500).json(errorResponse);
    }
});

app.post('/api/Search', async (req, res) => {
    if (!req.body) {
        const errorResponse = toErrorResponse('Request body is missing');
        return res.status(400).json(errorResponse);
    }
    console.log(req.body);
    const searchTerm = req.body.searchTerm;
    // if (searchTerm.length > 10) {
    //     const errorResponse = toErrorResponse('Search term too long');
    //     return res.status(400).json(errorResponse);
    // }
    const specialCharPattern = /[^a-zA-Z0-9 ]/;
    if (specialCharPattern.test(searchTerm)) {
        const errorResponse = toErrorResponse('Invalid input');
        return res.status(400).json(errorResponse);
    }

    try {
        const results = await searchProduct(searchTerm);
        const response = toResponse(results);
        res.status(200).json(response);
        console.log('POST request to /api/Search');
        console.log('Search term:', searchTerm);
        console.log('Search results:', results);
    } catch (error) {
        console.error('Error searching products:', error);
        const errorResponse = toErrorResponse('Internal Server Error');
        res.status(500).json(errorResponse);
    }
});
