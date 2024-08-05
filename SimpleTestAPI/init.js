const app = require('express')();
const PORT = 8080

app.listen(PORT, () => console.log(`Server is running on http://localhost:${PORT}`));

app.get('/', (req, res) => {
    res.send('Welcome to our e-commerce website!');
    console.log('GET request to /');
})

app.get('/api/products', (req, res) => {
    const products = [
        { id: 1, name: 'Product 1', price: 10.99 },
        { id: 2, name: 'Product 2', price: 15.99 },
        { id: 3, name: 'Product 3', price: 20.99 }
    ];
    res.json(products);
    console.log('GET request to /api/products');
})