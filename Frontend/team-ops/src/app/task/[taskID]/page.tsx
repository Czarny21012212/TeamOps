"use client";

import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

type task = {
    date: String,
    Content: String,
    id: number,
    title: String,
    difficultLevel: number,
    status: String
}

export default function Task(){

    const params = useParams()
    const taskId = params.taskId ? Number(params.taskId): undefined;

    const [task, setTask] = useState<task>({
        date: "",
        Content: "",
        id: 0,
        title: "",
        difficultLevel: 0,
        status: ""
    })

   useEffect(() => {
        fetch(`http://localhost:8081/api/getTask/${taskId}`, {
            method: "Get",
            credentials: 'include'
        })
        .then(response => {
            if(!response.ok){
                throw new Error("Network request is not ok")
            }
            return response.json()
        })
        .then(data => {
            setTask(data)
            console.log(data)
        })
        .catch(error => {
            console.log(error)
        })
   }, [])

    return(
        <div>

        </div>
    );

}