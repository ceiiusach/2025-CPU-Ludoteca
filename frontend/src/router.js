import { createRouter, createWebHistory } from "vue-router";

import Login from "@/views/Login.vue";
import Admin from "@/views/Admin.vue";

const routes = [
    {
        path: "/", redirect: {name: "Login"}
    },
    {
        path:"/login",
        name: "Login",
        component: Login
    },
    {
        path:"/admin",
        name: "Admin",
        component: Admin
    },
    {
        path: "/items",
        name: "items",
        component: () => import("@/views/items/index.vue")
    },
        {
        path: "/items/create",
        name: "create-item",
        component: () => import("@/views/items/create.vue")
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;