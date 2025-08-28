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
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;