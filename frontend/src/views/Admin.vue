<script setup>
import User from '@/components/User.vue';
import httpClient from '@/http-common';
import { shallowRef } from 'vue';

const users = shallowRef([]);

httpClient.get('/api/v1/user')
    .then((response) => {
        const temp = [];
        response.data.forEach((user) => {
            temp.push({
                type: User,
                props: {
                    id: user.id,
                    role: user.role,
                    email: user.email
                }
            });
        });
        users.value = temp;
    });
</script>

<template>
    <div class="min-h-screen w-screen bg-gray-100 p-8">
        <h1 class="text-3xl mb-4 font-bold">Usuarios</h1>
        <div class="grid grid-flow-row-dense gap-2">
            <div v-for="item in users">
                <Component :is="item.type" v-bind="item.props" />
            </div>
        </div>
    </div>
</template>