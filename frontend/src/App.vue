<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

function navigateHome() {
    router.push('/');
}

function navigate(event) {
    router.push(event.target.dataset.to);
}
</script>

<template>
    <div class="flex sticky justify-center top-0 p-4 bg-blue-500">
        <h1 class="w-fit text-center text-3xl font-bold cursor-pointer" @click="navigateHome">Ludoteca CEII</h1>
    </div>
    <div class="m-6">
        <RouterView id="router-view" />
    </div>
</template>

<style scoped></style>
