import os
import shutil

def move_files(source_dir, destination_dir):
    for filename in os.listdir(source_dir):
        source_file = os.path.join(source_dir, filename)
        if os.path.isfile(source_file):
            try:
                shutil.move(source_file, destination_dir)
                print(f"Moved file {source_file} to {destination_dir}")
            except Exception as e:
                print(f"Failed to move file {source_file}: {e}")

def delete_folder(folder):
    try:
        shutil.rmtree(folder)
        print(f"Deleted folder {folder}")
    except Exception as e:
        print(f"Failed to delete folder {folder}: {e}")

# Source and destination directories
source_dir = os.path.join('Machine Learning', 'data', 'non_infant_cry', 'convert_file')
destination_dir = os.path.join('Machine Learning', 'data', 'non_infant_cry')

# Move files
move_files(source_dir, destination_dir)

# Delete the convert_file folder
delete_folder(source_dir)
