import os
from pydub import AudioSegment

def delete_non_wav_files(source_dir):
    for filename in os.listdir(source_dir):
        file_path = os.path.join(source_dir, filename)
        if os.path.isfile(file_path) and not filename.endswith('.wav'):
            try:
                os.remove(file_path)
                print(f"Deleted non-wav file: {file_path}")
            except Exception as e:
                print(f"Failed to delete non-wav file {file_path}: {e}")

def convert_wav_to_mp3(source_dir):
    for root, dirs, files in os.walk(source_dir):
        for filename in files:
            if filename.endswith('.wav'):
                wav_path = os.path.join(root, filename)
                mp3_path = os.path.splitext(wav_path)[0] + '.mp3'
                try:
                    # Load .wav file
                    audio = AudioSegment.from_wav(wav_path)
                    # Export as .mp3
                    audio.export(mp3_path, format='mp3')
                    print(f"Converted {wav_path} to {mp3_path}")
                except Exception as e:
                    print(f"Failed to convert {wav_path}: {e}")

def delete_wav_files(source_dir):
    for root, dirs, files in os.walk(source_dir):
        for filename in files:
            if filename.endswith('.wav'):
                wav_path = os.path.join(root, filename)
                try:
                    os.remove(wav_path)
                    print(f"Deleted {wav_path}")
                except Exception as e:
                    print(f"Failed to delete {wav_path}: {e}")

# List of folders to process
folders_to_process = ['belly_pain', 'burping', 'discomfort', 'hungry', 'tired']

# Example usage
base_dir = os.path.abspath('E:/Bangkit/Capstone/Code/Criby/Machine Learning/data')

# Process each folder
for folder in folders_to_process:
    folder_path = os.path.join(base_dir, folder)
    if os.path.exists(folder_path):
        print(f"Processing folder: {folder_path}")
        delete_non_wav_files(folder_path)
        convert_wav_to_mp3(folder_path)
        delete_wav_files(folder_path)
    else:
        print(f"Folder not found: {folder_path}")
