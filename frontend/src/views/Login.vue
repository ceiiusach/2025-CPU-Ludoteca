<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import ImgLudoteca from '../imgs/images.jpg';
import FormRegister from '../components/FormRegister.vue';
import FormValidation from '../components/FormValidation.vue';
import MessageSucces from '../components/MessageSucces.vue';


const router = useRouter();
const email = ref('');
const emailRegister = ref('');
const password = ref('');
const errorMessage = ref(false);
const showModalRegister = ref(false);
const showModalValidation = ref(false);
const showModalSuccess = ref(false);


const handleLogin = () => {
  const data = {
    email: email.value,
    password: password.value
  };
  // Después de la autenticación, redirige al usuario a la página principal
  showModalSuccess.value = true;
  setTimeout(() => {
    showModalSuccess.value = false;
  }, 3000);
}

const showModalRegisterOpen = () => {
  showModalRegister.value = true;
}

</script>

<template>
  <div class="min-h-screen w-screen flex bg-gray-100">
    <FormRegister 
      v-if="showModalRegister" 
      @onCloseRegister="showModalRegister = false" 
      @onOpenValidation="showModalValidation = true"
    />

    <FormValidation v-if="showModalValidation" :Email="emailRegister"  @onCloseValidation="showModalValidation = false"/>
    <MessageSucces v-if="showModalSuccess" message="Inicio Sesion exitoso!" />
    <div class="w-1/2 h-screen bg-white p-8 flex flex-col justify-center items-center">
      <div class="w-full h-3/4 p-6 flex flex-col justify-center bg-white">
        <h1 class="text-4xl font-bold mb-2 text-center">Ludoteca CEII</h1>
        <p class="text-gray-600 mb-8 text-center">Bienvenido, por favor inicia sesion</p>
        <form @submit.prevent="handleLogin" class="space-y-4">
          <div class="mb-4">
            <label class="block text-gray-800 mb-2" for="email">Email</label>
            <input v-model="email" id="email" type="email" class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none" required />
          </div>
          <div class="mb-6">
            <label class="block text-gray-800 mb-2" for="password">Password</label>
            <input v-model="password" id="password" type="password" class="w-full px-3 py-2 border-2 rounded focus:border-blue-500 focus:outline-none" required />
          </div>
          <div class="mb-4 text-center" v-if="errorMessage">
            <p class="text-red-500 text-sm">Credenciales incorrectas, por favor intente de nuevo.</p>
          </div>
          <button type="submit"  class="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600">Ingresar</button>
          <p @click="showModalRegisterOpen" class="text-blue-500 text-sm font-semibold mt-4 block text-center hover:text-blue-700 cursor-pointer">No tienes cuenta? Registrate</p>
        </form>
      </div>
    </div>
    <div class="w-1/2 h-screen bg-blue-100 flex flex-col justify-center items-center">
        <img :src="ImgLudoteca" alt="Ludoteca Image" class="h-full w-auto cover" />
    </div>
  </div>
</template>