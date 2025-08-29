import httpClient from '@/http-common';

export type LoginRequest = {
  email: string
  password: string
}

export type AuthResponse = {
  token: string
}

const TOKEN_KEY = 'auth_token'

export async function login(payload: LoginRequest) {
  const { data } = await httpClient.post<AuthResponse>('/auth/login', payload)
  const token = data?.token
  if (!token) throw new Error('No se recibió token')

  localStorage.setItem(TOKEN_KEY, token)
  return token
}

export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY)
}

export function logout() {
  localStorage.removeItem(TOKEN_KEY)
}