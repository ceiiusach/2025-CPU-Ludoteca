<script setup>
const props = defineProps({
    id: String,
    userId: String,
    itemId: String,
    date: Date,
    estimatedMinutes: Number,
    pending: Boolean,
    userEmail: String,
    itemName: String
});
</script>

<template>
    <span v-if="props.pending">
        <div class="p-2 bg-gray-300 rounded hover:bg-gray-400">
            <h3 class="text-sm text-gray-800">PENDIENTE</h3>
            <h3 class="text-sm text-gray-600">{{ props.itemName }}</h3>
            <p>{{ props.userEmail }}</p>
            <p>{{ props.date }}</p>
            <p>{{ props.estimatedMinutes }} minutos</p>
        </div>
    </span>
    <span v-else>
        <div class="p-2 bg-gray-200 rounded hover:bg-gray-300">
            <h3 class="text-sm text-gray-600">{{ props.itemName }}</h3>
            <p>{{ props.userEmail }}</p>
            <p>{{ props.date }}</p>
            <p>{{ props.estimatedMinutes }} minutos</p>
        </div>
    </span>
</template>