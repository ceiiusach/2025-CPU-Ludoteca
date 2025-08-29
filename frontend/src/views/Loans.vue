<script setup>
import Loan from '@/components/Loan.vue';
import User from '@/components/User.vue';
import httpClient from '@/http-common';
import { shallowRef } from 'vue';

const loans = shallowRef([]);

httpClient.get('/api/v1/admin/loan')
    .then((response) => {
        const temp = [];
        console.log(response);
        response.data.forEach((loan) => {
            temp.push({
                type: Loan,
                props: {
                    id: loan.id,
                    userId: loan.user.id,
                    itemId: loan.item.id,
                    date: new Date(loan.date),
                    estimatedMinutes: loan.estimatedMinutes,
                    pending: loan.pending,
                    userEmail: loan.user.email,
                    itemName: loan.item.name
                }
            });
        });
        loans.value = temp;
    });
</script>

<template>
    <div class="min-h-screen w-screen bg-gray-100 p-8">
        <h1 class="text-3xl mb-4 font-bold">Préstamos</h1>
        <div class="grid grid-flow-row-dense gap-2">
            <div v-for="item in loans">
                <Component :is="item.type" v-bind="item.props" />
            </div>
        </div>
    </div>
</template>