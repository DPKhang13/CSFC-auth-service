import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import HomePage from "../pages/homePage";

const MainRoute: React.FC = () => {
    return (
        <BrowserRouter>
            <Routes>
                {/* Public routes */}
                <Route path="/" element={<HomePage />} />

                {/* User routes */}
                <Route element={<UserRoute />}>
                    <Route path="/home" element={<HomePage />} />
                </Route>

        {/* Admin routes */}
        <Route element={<ProtectedRoute role={["admin", "ADMIN"]} />}>
            <Route path="/admin" element={<AdminRoute />}>
                <Route index element={<Navigate to="/admin/category" replace />} />
                <Route path="category" element={<CategoryPage />} />
                <Route path="product" element={<ProductPage />} />
                <Route path="products/createProduct" element={<CreateProductPage />} />
                <Route path="products/:id" element={<UpdateProductPage />} />
            </Route>
        </Route>
    </Routes>
</BrowserRouter>

    );
};

export default MainRoute;
