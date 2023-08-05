from pdf2image import convert_from_path
import cv2
from PIL import Image
import os

def pdf_to_image(pdf_path, image_path):

    poppler_path = "/usr/bin"  


    redactions = [
        [
            {
                "top_left_x": 612,
                "top_left_y": 1065,
                "bottom_right_x": 1060,
                "bottom_right_y": 1145,
                "text": "123458",
            },
            {
                "top_left_x": 788,
                "top_left_y": 1278,
                "bottom_right_x": 1225,
                "bottom_right_y": 1337,
                "text": "Consultation",
            },
        ],
        [
            {
                "top_left_x": 771,
                "top_left_y": 283,
                "bottom_right_x": 1058,
                "bottom_right_y": 321,
                "text": "Objet",
            },
             {
                "top_left_x": 546,
                "top_left_y": 345,
                "bottom_right_x": 868,
                "bottom_right_y": 380,
                "text": "Conditions",
            },
            
             {
                "top_left_x": 1000,
                "top_left_y": 1008,
                "bottom_right_x": 1287,
                "bottom_right_y": 1041,
                "text": "123458",
            },
            
        ],
         [
            {
                "top_left_x": 787,
                "top_left_y": 608,
                "bottom_right_x": 1008,
                "bottom_right_y": 640,
                "text": "delai",
            },
        
            
        ],
        [],
        [],
        [],
        [],
        [],
            

    ]


    images = convert_from_path(pdf_path, poppler_path=poppler_path)


    for i, image in enumerate(images):

        image.save(f"{image_path}_page{i}.png", "PNG")


        img = cv2.imread(f"{image_path}_page{i}.png")


        page_redactions = redactions[i]

        for redaction_info in page_redactions:

            top_left_x = redaction_info["top_left_x"]
            top_left_y = redaction_info["top_left_y"]
            bottom_right_x = redaction_info["bottom_right_x"]
            bottom_right_y = redaction_info["bottom_right_y"]
            text = redaction_info["text"]


            white = (255, 255, 255)
            img[top_left_y:bottom_right_y, top_left_x:bottom_right_x] = white


            font = cv2.FONT_HERSHEY_SIMPLEX
            org = (top_left_x + int((bottom_right_x - top_left_x) / 4), top_left_y + int((bottom_right_y - top_left_y) / 2))
            fontScale = 1
            color = (0, 0, 0)
            thickness = 2
            img = cv2.putText(img, text, org, font, fontScale, color, thickness, cv2.LINE_AA)


        cv2.imwrite(f"{image_path}_redacted_page{i}.png", img)


    redacted_images = []
    for i in range(len(images)):
        image = Image.open(f"{image_path}_redacted_page{i}.png")
        redacted_images.append(image)
        os.remove(f"{image_path}_redacted_page{i}.png")


    redacted_images[0].save(f"{image_path}_redacted.pdf", save_all=True, append_images=redacted_images[1:], resolution=100.0, quality=95)


    for i in range(len(images)):
        os.remove(f"{image_path}_page{i}.png")


pdf_to_image('/home/mohamed/Downloads/Mini CC 27-04-2023 sans retenue de garantie.pdf', 'document')

