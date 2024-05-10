import axios from "axios";

export const sendLinkAPI = async (
  link: string,
  original: string,
  token: string,
) => {
  try {
    return await axios
      .post(
        `${process.env.NEXT_PUBLIC_URL}/links`,
        {
          link,
          original,
        },
        {
          headers: {
            Authorization: token,
          },
        },
      )
      .then((response) => {
        return response.status;
      });
  } catch (e) {
    console.log(e);
  }
};

export const recoveryLinkAPI = async (token: string) => {
  try {
    return await axios
      .get(`${process.env.NEXT_PUBLIC_URL}/links`, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        return response.data;
      });
  } catch (e) {
    console.log(e);
  }
};

export const removeLinkAPI = async (token: string, id: string) => {
  try {
    return await axios
      .delete(`${process.env.NEXT_PUBLIC_URL}/links/${id}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        return response.data;
      });
  } catch (e) {
    console.log(e);
  }
};

export const updateLinkAPI = async (
  token: string,
  id: string,
  link: string,
  original: string,
) => {
  try {
    return await axios
      .put(
        `${process.env.NEXT_PUBLIC_URL}/links/${id}`,
        {
          link,
          original,
        },
        {
          headers: {
            Authorization: token,
          },
        },
      )
      .then((response) => {
        return response.data;
      });
  } catch (e) {
    console.log(e);
  }
};

export const findByLink = async (token: string, id: string) => {
  try {
    return await axios
      .get(`${process.env.NEXT_PUBLIC_URL}/links/${id}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        return response.data;
      });
  } catch (e) {
    console.log(e);
  }
};
