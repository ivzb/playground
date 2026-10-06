// IProductInterface.aidl
package com.example.productserviceconsumer;

import com.example.productserviceconsumer.Product;
import com.example.productserviceconsumer.IProductCallback;

interface IProductInterface {

    void fetchProducts(IProductCallback callback);

    void unregisterCallback(IProductCallback callback);

}