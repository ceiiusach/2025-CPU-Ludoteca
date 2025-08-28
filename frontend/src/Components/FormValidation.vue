<script setup>
import { ref, defineProps, onMounted } from 'vue';
import MessageSuccess from './MessageSucces.vue';

const props = defineProps({
    Email: {
        type: String,
        required: true
    }
});

const error = ref(false);
const success = ref(false);
const digits = Array.from({ length: 6 }, () => ref(''));
const inputs = ref([]);

const emit = defineEmits(['onCloseValidation']);

const showEmail = ref(props.Email || '');

onMounted(() => {
    if (!showEmail.value) {
        emit('onCloseValidation');
    }
})

// función para moverse automáticamente al siguiente input
const handleInput = (index, event) => {
  let val = event.target.value.toUpperCase().replace(/[^A-Z0-9]/g, ''); 
  digits[index].value = val; 

  if (val && index < inputs.value.length - 1) {
    inputs.value[index + 1].focus();
  }
};

// función para retroceder con backspace
const handleBackspace = (index, event) => {
  if (event.key === 'Backspace' && !digits[index].value && index > 0) {
    inputs.value[index - 1].focus();
  }
};

const sendRecoveryEmail = async () => {
  error.value = false;
  try {
    /*const response = await ForgotPassword(email);
    if (response.status === 200) {
      success.value = true;
      setTimeout(() => (success.value = false), 3000);
    } else error.value = true;
     */
  } catch (e) {
    console.error(e);
    alert('Error al enviar el código.');
  }
};

const verify = async () => {
  const code = digits.map(d => d.value).join('');
  if (code.length < 6) {
    alert('Por favor, ingresa el código completo.');
    return;
  }

  try {
    console.log("Verifying code for email:", email, "with code:", code);
    /*const response = await VerifyCode(email, code);
    if (response.status === 200) {
      success.value = true;
      setTimeout(() => {
        success.value = false;
        router.push({ name: 'ResetPassword', query: { email, code }});
      }, 3000);
    } else{
        error.value = true;
        console.error("Error verifying code:", response.data);
    }*/
  } catch (e) {
    console.error(e);
    alert('Error al verificar el código.');
  }
};
</script>

<template>
    <div class="fixed inset-0 flex items-center justify-center z-[9999] bg-black/30">
        <MessageSuccess v-if="success" message="Código Validado" />
        <div class="w-[70rem] h-[30rem] bg-white rounded-xl shadow-lg p-8 flex flex-col justify-between relative">
        <button
            class="absolute top-2 right-4 text-gray-400 hover:text-gray-500 text-2xl font-bold focus:outline-none"
            @click="emit('onCloseValidation')"
            aria-label="Cerrar"
          >
            &times;
        </button>
        <h1 class="text-5xl font-bold text-center text-blue-600">Ingresa el código</h1>
        <p class="text-lg text-gray-700 text-center mt-4">
            Se envió un código a {{ showEmail }}
        </p>

            <!-- inputs -->
        <div class="flex justify-center gap-3 mt-6 h-1/2">
            <input
                v-for="(digit, i) in digits"
                :key="i"
                ref="inputs"
                v-model="digit.value"
                type="text"
                maxlength="1"
                class="w-1/6 h-full bg-white text-center text-black text-5xl font-bold uppercase border-2 border-blue-400 rounded-lg focus:outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-500"
                @input="handleInput(i, $event)"
                @keydown="handleBackspace(i, $event)"
                />
        </div>

            <p class="text-blue-700 text-lg mt-5 font-semibold text-center cursor-pointer hover:underline" @click="sendRecoveryEmail">
                Reenviar código
            </p>
            <p v-if="error" class="text-red-500 font-semibold">Codigo invalido, porfavor intentelo nuevamente</p>
            <div class="flex justify-center mt-6">
                <button
                        class="bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-2 rounded-lg transition"
                        @click="verify"
                    >
                    Verificar código
                </button>
            </div>
        </div>
    </div>
</template>