
function toResponse(products) {
    return {
        OUT_STAT: "T",
        OUT_MESS: "Success",
        OUT_DATA: products.map(product => ({
            CD_BRAND: product.CD_BRAND,
            DESC_BRAND: product.DESC_BRAND
        }))
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
}