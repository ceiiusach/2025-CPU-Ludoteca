import { createRouter, createWebHistory } from "vue-router";

import Login from "@/views/Login.vue";
import Admin from "@/views/Admin.vue";
import Items from "@/views/Items.vue";
import CreateItem from "@/views/CreateItem.vue";
import Loans from "@/views/Loans.vue";

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
        component: Items
    },
        {
        path: "/items/create",
        name: "create-item",
        component: CreateItem
    },
    {
        path: "/loans",
        name: "Loans",
        component: Loans
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;