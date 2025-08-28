<script setup>
import { defineProps } from 'vue'

defineProps({
  message: String
})
</script>

<template>
  <div class="fixed inset-0 flex items-center justify-center z-[9999] bg-black/30">
    <div
      class="bg-white text-admin-primary p-6 rounded-xl shadow-lg w-full max-w-sm flex flex-col items-center gap-4 animate-scale-up"
    >
      <!-- Ícono de check minimalista -->
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-16 w-16 text-green-500"
        viewBox="0 0 24 24"
        fill="currentColor"
      >
        <circle cx="12" cy="12" r="10" fill="currentColor" />
        <path
          fill="#fff"
          d="M16.2 8.7a1 1 0 0 0-1.4 0L10 13.5l-2.8-2.8a1 1 0 0 0-1.4 1.4l3.5 3.5a1 1 0 0 0 1.4 0l5.5-5.5a1 1 0 0 0 0-1.4z"
        />
      </svg>

      <p class="text-center font-semibold text-lg text-gray-700">
        {{ message }}
      </p>
    </div>
  </div>
</template>

<style scoped>
@keyframes scaleUp {
  0% {
    transform: scale(0.85);
    opacity: 0;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

.animate-scale-up {
  animation: scaleUp 0.25s ease-out forwards;
}
</style>