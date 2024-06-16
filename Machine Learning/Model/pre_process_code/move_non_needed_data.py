import os
import shutil

def move_folders(source_dir, destination_dir, exclude_folder):
    for folder_name in os.listdir(source_dir):
        folder_path = os.path.join(source_dir, folder_name)
        if os.path.isdir(folder_path) and folder_name != exclude_folder:
            try:
                shutil.move(folder_path, destination_dir)
                print(f"Moved folder {folder_path} to {destination_dir}")
            except Exception as e:
                print(f"Failed to move folder {folder_path}: {e}")

# Source and destination directories
source_dir = os.path.join('Machine Learning', 'data_wav', 'non_infant_cry')
destination_dir = os.path.join('Machine Learning', 'original_data')
exclude_folder = 'convert_file'

# Move folders
move_folders(source_dir, destination_dir, exclude_folder)
