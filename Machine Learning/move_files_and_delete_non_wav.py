import os
from pydub import AudioSegment
import shutil

def delete_non_wav_files(source_dir):
    for filename in os.listdir(source_dir):
        file_path = os.path.join(source_dir, filename)
        if os.path.isfile(file_path) and not filename.endswith('.wav'):
            try:
                os.remove(file_path)
                print(f"Deleted non-wav file: {file_path}")
            except Exception as e:
                print(f"Failed to delete non-wav file {file_path}: {e}")

def move_files_from_subdirs(parent_dir, destination_dir):
    if not os.path.exists(destination_dir):
        os.makedirs(destination_dir)
    
    for root, dirs, files in os.walk(parent_dir):
        # Skip the root folder itself
        if root == parent_dir:
            continue
        
        for filename in files:
            file_path = os.path.join(root, filename)
            
            if os.path.isfile(file_path):
                dest_path = os.path.join(destination_dir, filename)
                
                # Check if the file already exists in the destination directory
                if os.path.exists(dest_path):
                    print(f"File {dest_path} already exists. Skipping.")
                else:
                    shutil.move(file_path, dest_path)
                    print(f"Moved {file_path} to {dest_path}")

# Example usage
parent_dir = os.path.join('Machine Learning', 'data_wav', 'non_infant_cry', 'convert_file')
destination_dir = os.path.join('Machine Learning', 'data_wav', 'non_infant_cry')
source_dir = os.path.join('Machine Learning', 'data_wav', 'non_infant_cry', 'convert_file')

# Assuming current working directory is 'Criby'
move_files_from_subdirs(parent_dir, destination_dir)