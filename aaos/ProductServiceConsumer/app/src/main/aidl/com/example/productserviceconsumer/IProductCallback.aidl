// IProductCallback.aidl
package com.example.productserviceconsumer;

import com.example.productserviceconsumer.Product;

interface IProductCallback {

    void onSuccess(in List<Product> products);

    void onError(String message);

}