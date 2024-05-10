import { useEffect, useState } from "react";
import { recoveryLinkAPI, removeLinkAPI } from "@/services/link";
import Router, { useRouter } from "next/router";
import Image from "next/image";
import { IconDelete, IconEdit } from "@/assets";

export interface links {
  createdAt: string;
  id: string;
  link: string;
  original: string;
  updatedAt: string;
}

export const Result = () => {
  const [item, setItem] = useState<links[]>();
  const [tokenValue, setTokenValue] = useState("");
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setTokenValue(token);
      recoveryLinkAPI(token).then((r) => {
        setItem(r);
      });
    }
  }, []);

  const handleDelete = async (id: string) => {
    await removeLinkAPI(tokenValue, id);
    Router.reload();
  };

  return (
    <>
      <div className="mt-10 w-[95%] rounded bg-[#1C283F] p-5 font-bold">
        <h3 className="text-Lite lg:text-xl xl:text-2xl">Shorten Links</h3>
      </div>
      {item &&
        item.map((link) => (
          <div
            className="my-1 flex w-[95%]  items-center rounded bg-Grey p-5  text-Lite lg:text-xl xl:text-2xl"
            key={link.id}
          >
            <div className="flex w-full flex-wrap">
              <a
                key={link.id}
                target="_blank"
                href={`${link.original}`}
                rel="noopener noreferrer"
              >
                {link.link}
              </a>
            </div>

            <button
              onClick={() => router.push(`links/${link.id}`)}
              title={"Edit"}
            >
              <Image
                src={IconEdit}
                alt="edit"
                className="ml-2 mr-5 h-7 w-7 rounded-full bg-white p-1 "
              />
            </button>

            <button onClick={() => handleDelete(link.id)} title={"Delete"}>
              <Image
                src={IconDelete}
                alt="delete"
                className="ml-2 mr-5 h-7 w-7 rounded-full bg-white p-1"
              />
            </button>
          </div>
        ))}
    </>
  );
};
