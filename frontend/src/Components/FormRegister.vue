<script setup>
import {ref} from 'vue';

const emit = defineEmits(['onCloseRegister', 'onOpenValidation']);

const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const errorMessage = ref('');

const SubmitRegister = async () => {
  if (password.value !== confirmPassword.value) {
    errorMessage.value = "Las contraseñas no coinciden.";
    return;
  }

  const data = {
    email: email.value,
    password: password.value
  };

  // Aquí iría la lógica para enviar los datos al servidor
  // Si es exitoso:
  errorMessage.value = "";
  emit("onCloseRegister");       // cerrar modal de registro
  emit("onOpenValidation");     // abrir modal de validación en el padre
};
</script>

<template>
  <div class="fixed inset-0 flex items-center justify-center z-[9999] bg-black/30">
        <div class="bg-white h-[30rem] w-[25rem] text-admin-primary p-6 rounded-xl shadow-lg flex flex-col items-center gap-4 animate-scale-up relative">
          <button
            class="absolute top-2 right-4 text-gray-400 hover:text-gray-500 text-2xl font-bold focus:outline-none"
            @click="emit('onCloseRegister')"
            aria-label="Cerrar"
          >
            &times;
          </button>
      <h2 class="text-2xl font-bold m-4">Registro</h2>
      <form 
        class="w-full h-full p-4 flex flex-col justify-between text-center bg-white"
        @submit.prevent="SubmitRegister"
      >
        <div>
          <label class="block text-gray-800 mb-2" for="Email">Correo institucional</label>
          <input v-model="email" id="Email" type="email" class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none" required />
        </div>
        <div>
          <label class="block text-gray-800 mb-2" for="Password">Contraseña</label>
          <input v-model="password" id="Password" type="password" class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none" required />
        </div>
        <div>
          <label class="block text-gray-800 mb-2" for="ConfirmPassword">Confirmar Contraseña</label>
          <input v-model="confirmPassword" id="ConfirmPassword" type="password" class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none" required />
        </div>
        <p class="text-red-500 text-sm" v-if="errorMessage">{{ errorMessage }}</p>
        <button type="submit" class="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600">Registrarse</button>
      </form>
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