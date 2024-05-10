import axios from "axios";

interface dataProps {
  login: string;
  password: string;
  role?: string;
}

export const loginAPI = async (data: dataProps) => {
  try {
    return await axios
      .post(`${process.env.NEXT_PUBLIC_URL}/auth/login`, {
        login: data.login,
        password: data.password,
        role: data.role,
      })
      .then((response) => {
        localStorage.setItem("token", response.data.token);
        return response.status;
      });
  } catch (e) {
    console.log(e);
  }
};

export const registerAPI = async (data: dataProps) => {
  try {
    return await axios
      .post(`${process.env.NEXT_PUBLIC_URL}/auth/register`, {
        login: data.login,
        password: data.password,
        role: data.role,
      })
      .then((response) => {
        return response.status;
      });
  } catch (e) {
    console.log(e);
  }
};
