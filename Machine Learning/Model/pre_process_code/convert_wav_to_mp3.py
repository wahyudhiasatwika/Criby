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
    for filename in os.listdir(source_dir):
        if filename.endswith('.wav'):
            wav_path = os.path.join(source_dir, filename)
            mp3_path = os.path.join(source_dir, os.path.splitext(filename)[0] + '.mp3')
            
            try:
                # Load .wav file
                audio = AudioSegment.from_wav(wav_path)
                # Export as .mp3
                audio.export(mp3_path, format='mp3')
                print(f"Converted {wav_path} to {mp3_path}")
            except Exception as e:
                print(f"Failed to convert {wav_path}: {e}")

def delete_wav_files(source_dir):
    for filename in os.listdir(source_dir):
        if filename.endswith('.wav'):
            wav_path = os.path.join(source_dir, filename)
            try:
                os.remove(wav_path)
                print(f"Deleted {wav_path}")
            except Exception as e:
                print(f"Failed to delete {wav_path}: {e}")

# Example usage
source_dir = os.path.abspath(os.path.join('Machine Learning', 'data', 'non_infant_cry', 'convert_file'))

# Assuming current working directory is 'Criby'
delete_non_wav_files(source_dir)
convert_wav_to_mp3(source_dir)
delete_wav_files(source_dir)
