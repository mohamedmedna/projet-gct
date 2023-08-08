from pdf2image import convert_from_bytes
import cv2
from PIL import Image
import os
import sys
import json
import base64

def pdf_to_image(pdf_content, image_folder_path, redactions_json):
    redactions = json.loads(redactions_json)

    pdf_content = base64.b64decode(pdf_content)

    images = convert_from_bytes(pdf_content)

    for i, image in enumerate(images):
        image.save(f"{image_folder_path}_page{i}.png", "PNG")

        img = cv2.imread(f"{image_folder_path}_page{i}.png")

        page_redactions = redactions[i]

        for redaction_info in page_redactions:
            top_left_x = int(redaction_info["top_left_x"])
            top_left_y = int(redaction_info["top_left_y"])
            bottom_right_x = int(redaction_info["bottom_right_x"])
            bottom_right_y = int(redaction_info["bottom_right_y"])
            x, y, width, height = top_left_x, top_left_y, (bottom_right_x - top_left_x), (bottom_right_y - top_left_y)
            text = redaction_info["text"]

            white = (255, 255, 255)
            img[y:y + height, x:x + width] = white

            font = cv2.FONT_HERSHEY_COMPLEX
            org = (x + int(width / 4), y + int(height / 2))
            fontScale = 1
            color = (0, 0, 0)
            thickness = 2

            lines = text.split('\n')


            for line in lines:
                img = cv2.putText(img, line, org, font, fontScale, color, thickness, cv2.LINE_AA)


        cv2.imwrite(f"{image_folder_path}_redacted_page{i}.png", img)

    redacted_images = []
    for i in range(len(images)):
        image = Image.open(f"{image_folder_path}_redacted_page{i}.png")
        redacted_images.append(image)
        os.remove(f"{image_folder_path}_redacted_page{i}.png")

    redacted_images[0].save(
        f"{image_folder_path}_redacted.pdf",
        save_all=True,
        append_images=redacted_images[1:],
        resolution=100.0,
        quality=95
    )

    for i in range(len(images)):
        os.remove(f"{image_folder_path}_page{i}.png")

if __name__ == "__main__":
    if len(sys.argv) < 4:
        print("Usage: python pdf_redaction.py <pdf_content> <image_folder_path> <redactions_json>")
        sys.exit(1)

    pdf_content = sys.argv[1]
    image_folder_path = sys.argv[2]
    redactions_json = sys.argv[3]

    pdf_to_image(pdf_content, image_folder_path, redactions_json)

