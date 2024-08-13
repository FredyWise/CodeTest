
function toResponse(products) {
    return {
        OUT_STAT: "T",
        OUT_MESS: "Success",
        OUT_DATA: products.map(product => ({
            id: product.id,
            name: product.name,
            price: product.price
        }))
    }
}

function toSuccessResponse(message) {
    return {
        OUT_STAT: "T",
        OUT_MESS: message,
        OUT_DATA: []
    }
}

function toErrorResponse(message){
    return{
        OUT_STAT: "F",
        OUT_MESS: message,
        OUT_DATA: []
    };
}

module.exports = {
    toResponse,
    toErrorResponse,
    toSuccessResponse,
}