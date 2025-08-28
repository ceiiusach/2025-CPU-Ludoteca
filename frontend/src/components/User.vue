<script setup>
const props = defineProps({
    id: String,
    role: String,
    email: String,
});
</script>

<template>
    <span v-if="props.role == 'ADMIN'">
        <div class="p-2 bg-gray-200 rounded hover:bg-gray-300">
            <h3 class="text-sm text-gray-600">Administrador</h3>
            <h2>{{ props.email }}</h2>
        </div>
    </span>
    <span v-else>
        <div class="p-2 bg-gray-200 rounded hover:bg-gray-300">
            <h3 class="text-sm text-gray-600">Estudiante</h3>
            <h2>{{ props.email }}</h2>
        </div>
    </span>
</template>