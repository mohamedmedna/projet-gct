from docx import Document
import json
import base64
import sys
import os

def modify_word_document(base64_encoded_content, doc_folder_path, replacements):
    doc_content = base64.b64decode(base64_encoded_content)
    
    save_filename = os.path.join(doc_folder_path, "output_temp.docx")
    
    with open(save_filename, "wb") as temp_file:
        temp_file.write(doc_content)
        
    document = Document(save_filename)

    def find_replace(replacements, paragraph):
        for keyword, new_value in replacements.items():
            if keyword in paragraph.text:
                paragraph.text = paragraph.text.replace(keyword, new_value)

    for paragraph in document.paragraphs:
        find_replace(replacements, paragraph)

    modified_filename = os.path.join(doc_folder_path, "output_modified.docx")
    document.save(modified_filename)

    
    os.remove(save_filename)

if __name__ == "__main__":
    if len(sys.argv) < 4:
        print("Usage: python modified_docx.py <docx_content> <doc_folder_path> <replacements_file_path>")
        sys.exit(1)

    docx_content = sys.argv[1]
    doc_folder_path = sys.argv[2]
    replacements_file_path = sys.argv[3]

    with open(replacements_file_path, 'r') as file:
        replacements_str = file.read()

    replacements = json.loads(replacements_str)

    modify_word_document(docx_content, doc_folder_path, replacements)

