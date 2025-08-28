import { createRouter, createWebHistory } from "vue-router";

import Login from "./Login.vue";

const routes = [
    {
        path: "/", redirect: {name: "Login"}
    },
    {
        path:"/login",
        name: "Login",
        component: Login
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;